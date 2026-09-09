import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CategoryAverageRating {

    public static class RatingMapper
            extends Mapper<Object, Text, Text, DoubleWritable> {

        private final Text category = new Text();
        private final DoubleWritable rating = new DoubleWritable();

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            String line = value.toString().trim();

            if (line.isEmpty()) {
                return;
            }

            String[] fields = line.split("\\t", -1);

            // Dataset format:
            // video_id, uploader, age, category, length, views, rate, ...

            if (fields.length >= 7) {
                try {
                    String cat = fields[3].trim();
                    double rate = Double.parseDouble(fields[6].trim());

                    if (!cat.isEmpty()) {
                        category.set(cat);
                        rating.set(rate);
                        context.write(category, rating);
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid rating values
                }
            }
        }
    }

    public static class RatingReducer
            extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

        private final DoubleWritable result = new DoubleWritable();

        public void reduce(Text key, Iterable<DoubleWritable> values,
                           Context context)
                throws IOException, InterruptedException {

            double totalRating = 0.0;
            long count = 0;

            for (DoubleWritable value : values) {
                totalRating += value.get();
                count++;
            }

            if (count > 0) {
                result.set(totalRating / count);
                context.write(key, result);
            }
        }
    }

    public static void main(String[] args)
            throws Exception {

        if (args.length != 2) {
            System.err.println(
                "Usage: CategoryAverageRating <input path> <output path>"
            );
            System.exit(2);
        }

        Configuration conf = new Configuration();

        Job job = Job.getInstance(
            conf, "Category Wise Average Rating"
        );

        job.setJarByClass(CategoryAverageRating.class);

        job.setMapperClass(RatingMapper.class);
        job.setReducerClass(RatingReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(
            job, new Path(args[0])
        );

        FileOutputFormat.setOutputPath(
            job, new Path(args[1])
        );

        System.exit(
            job.waitForCompletion(true) ? 0 : 1
        );
    }
}