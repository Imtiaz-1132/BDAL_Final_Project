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

grouped = GROUP data BY category;

result = FOREACH grouped GENERATE
    group,
    SUM(data.views);

DUMP result;