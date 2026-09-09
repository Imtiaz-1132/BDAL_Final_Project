# BDAL Final Project

## Big Data Analytics Using HDFS, MapReduce and Apache Pig

This project demonstrates a complete Big Data Analytics workflow on a large YouTube video dataset using **HDFS**, **Hadoop MapReduce**, and **Apache Pig**.

The project focuses on analyzing video categories, total views, and average ratings and comparing the results obtained from MapReduce and Pig.

---

## 1. Project Objectives

The main objectives of this project are:

1. Store a large dataset in HDFS.
2. Verify HDFS storage, blocks, replication, and data integrity.
3. Perform category-based analysis using Hadoop MapReduce.
4. Perform the same analysis using Apache Pig.
5. Compare the results of MapReduce and Pig.
6. Present the analytical results using visualizations.

---

## 2. Dataset

The dataset contains YouTube video information with fields such as:

- `video_id`
- `uploader`
- `age`
- `category`
- `length`
- `views`
- `rate`
- `ratings`
- `comments`
- Related video IDs

### Dataset Summary

| Item | Value |
|---|---:|
| Total records | 749,361 |
| Input files | 5 |
| HDFS blocks | 6 |
| HDFS replication factor | 1 |
| DataNode | 1 |
| Categories with names | 13 |
| Empty category records | 5,792 |

The raw dataset is intentionally **not included in this GitHub repository** because of its size.

---

## 3. Technologies Used

- **Hadoop 3.2.4**
- **HDFS**
- **Hadoop MapReduce**
- **Apache Pig 0.18.0**
- **Java 8**
- **Windows CMD**
- **Git & GitHub**

---

## 4. HDFS Implementation

The dataset was uploaded to:

```text
/bda_final_project/input
```

The HDFS structure is:

```text
/bda_final_project
├── input
│   ├── 0.txt
│   ├── 1.txt
│   ├── 2.txt
│   ├── 3.txt
│   └── 4.txt
└── output
```

HDFS verification confirmed:

- 6 total blocks
- Replication factor = 1
- Missing blocks = 0
- Corrupt blocks = 0
- Under-replicated blocks = 0
- HDFS input record count = **749,361**

Detailed HDFS commands are available in [`hdfs/commands.txt`](hdfs/commands.txt).

---

## 5. MapReduce Analysis

Three MapReduce programs were implemented.

### 5.1 Category Count

`CategoryCount.java` counts the number of videos in each category.

Main result:

| Category | Video Count |
|---|---:|
| Music | 179,049 |
| Entertainment | 127,674 |
| Comedy | 87,818 |
| Film & Animation | 73,293 |
| Sports | 67,329 |
| Gadgets & Games | 59,817 |
| People & Blogs | 48,890 |
| News & Politics | 35,925 |
| Howto & DIY | 18,257 |
| Travel & Places | 14,675 |
| Autos & Vehicles | 14,284 |
| Pets & Animals | 10,496 |
| UNA | 6,062 |

The dataset also contains **5,792 records with an empty category**.

### 5.2 Total Views by Category

`CategoryTotalViews.java` calculates the total number of views for each category.

| Category | Total Views |
|---|---:|
| Music | 2,426,199,511 |
| Entertainment | 1,644,510,629 |
| Comedy | 1,603,337,065 |
| Film & Animation | 659,449,540 |
| Sports | 647,412,772 |
| People & Blogs | 425,607,955 |
| Gadgets & Games | 505,658,305 |
| News & Politics | 310,502,116 |
| Howto & DIY | 252,583,445 |
| UNA | 254,936,053 |
| Autos & Vehicles | 131,705,784 |
| Pets & Animals | 118,786,665 |
| Travel & Places | 57,748,080 |

**Music** has the highest total views.

### 5.3 Average Rating by Category

`CategoryAverageRating.java` calculates the average video rating for each category.

| Category | Average Rating |
|---|---:|
| Film & Animation | 4.0777 |
| Music | 3.9583 |
| UNA | 3.9459 |
| Entertainment | 3.7990 |
| Gadgets & Games | 3.7384 |
| Sports | 3.5107 |
| News & Politics | 3.4930 |
| Howto & DIY | 3.4060 |
| Comedy | 3.3996 |
| Autos & Vehicles | 3.1326 |
| People & Blogs | 3.0029 |
| Pets & Animals | 2.9414 |
| Travel & Places | 2.6898 |

**Film & Animation** has the highest average rating.

---

## 6. Apache Pig Analysis

The same dataset was analyzed using Apache Pig.

Pig scripts:

- [`load_data.pig`](pig/load_data.pig)
- [`count_data.pig`](pig/count_data.pig)
- [`category_count.pig`](pig/category_count.pig)
- [`category_total_views.pig`](pig/category_total_views.pig)
- [`category_average_rating.pig`](pig/category_average_rating.pig)

The Pig results matched the MapReduce results for the corresponding analyses.

The total record count produced by Pig was:

```text
749361
```

---

## 7. Result Visualizations

### Video Count by Category

![Video Count by Category](screenshots/visualizations/category_video_count.png)

### Total Views by Category

![Total Views by Category](screenshots/visualizations/category_total_views.png)

### Average Rating by Category

![Average Rating by Category](screenshots/visualizations/category_average_rating.png)

---

## 8. MapReduce and Pig Comparison

| Analysis | MapReduce | Pig |
|---|---|---|
| Total record count | 749,361 | 749,361 |
| Category count | Completed | Completed |
| Total views by category | Completed | Completed |
| Average rating by category | Completed | Completed |
| Result consistency | Matches Pig | Matches MapReduce |

This confirms that both approaches produced consistent analytical results on the dataset.

---

## 9. Repository Structure

```text
BDAL_Final_Project/
├── hdfs/
│   └── commands.txt
├── mapreduce/
│   ├── src/
│   │   ├── CategoryCount.java
│   │   ├── CategoryTotalViews.java
│   │   └── CategoryAverageRating.java
│   ├── CategoryCount.jar
│   ├── CategoryTotalViews.jar
│   └── CategoryAverageRating.jar
├── pig/
│   ├── load_data.pig
│   ├── count_data.pig
│   ├── category_count.pig
│   ├── category_total_views.pig
│   └── category_average_rating.pig
├── screenshots/
├── docs/
├── .gitignore
└── README.md
```

---

## 10. Key Findings

- **Music** contains the largest number of videos: **179,049**.
- **Music** also has the highest total views: **2,426,199,511**.
- **Film & Animation** has the highest average rating: approximately **4.08**.
- The HDFS dataset contains **749,361 records**.
- HDFS verification found no missing or corrupt blocks.
- MapReduce and Pig produced consistent results.

---

## 11. Conclusion

This project demonstrates how a large YouTube dataset can be stored and processed using a Hadoop-based Big Data environment.

HDFS was used for distributed storage, MapReduce was used for programmatic data processing, and Apache Pig was used for higher-level data analysis. The consistency between MapReduce and Pig results provides confidence in the analytical processing.

The visualizations make the major category-level patterns easier to understand and present.

---

## 12. Project Evidence

Execution screenshots are available in the [`screenshots`](screenshots/) directory, including HDFS, MapReduce, and Pig execution results.

The project source code, scripts, JAR files, commands, and result evidence are organized inside this repository.

