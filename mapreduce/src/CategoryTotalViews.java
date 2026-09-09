import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CategoryTotalViews {

    public static class ViewsMapper
            extends Mapper<Object, Text, Text, LongWritable> {

        private final Text category = new Text();
        private final LongWritable views = new LongWritable();

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            String line = value.toString().trim();

            if (line.isEmpty()) {
                return;
            }

            String[] fields = line.split("\\t", -1);

            // Dataset format:
            // video_id, uploader, age, category, length, views, ...

            if (fields.length >= 6) {
                try {
                    String cat = fields[3].trim();
                    long viewCount = Long.parseLong(fields[5].trim());

                    if (!cat.isEmpty()) {
                        category.set(cat);
                        views.set(viewCount);
                        context.write(category, views);
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid view values
                }
            }
        }
    }

    public static class ViewsReducer
            extends Reducer<Text, LongWritable, Text, LongWritable> {

        private final LongWritable result = new LongWritable();

        public void reduce(Text key, Iterable<LongWritable> values,
                           Context context)
                throws IOException, InterruptedException {

            long totalViews = 0;

            for (LongWritable value : values) {
                totalViews += value.get();
            }

            result.set(totalViews);
            context.write(key, result);
        }
    }

    public static void main(String[] args)
            throws Exception {

        if (args.length != 2) {
            System.err.println(
                "Usage: CategoryTotalViews <input path> <output path>"
            );
            System.exit(2);
        }

        Configuration conf = new Configuration();

        Job job = Job.getInstance(
            conf, "Category Wise Total Views"
        );

        job.setJarByClass(CategoryTotalViews.class);

        job.setMapperClass(ViewsMapper.class);
        job.setReducerClass(ViewsReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

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