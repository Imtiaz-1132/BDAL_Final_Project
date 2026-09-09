data = LOAD '/bda_final_project/input/*.txt'
USING PigStorage('\t')
AS (
    video_id:chararray,
    uploader:chararray,
    age:int,
    category:chararray,
    length:int,
    views:long,
    rate:double,
    ratings:int,
    comments:int
);

total_count = GROUP data ALL;

result = FOREACH total_count GENERATE COUNT(data);

DUMP result;