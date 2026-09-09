# BDAL Final Project

## Big Data Analytics using HDFS, MapReduce and Apache Pig

### Project Overview

This project demonstrates practical Big Data Analytics operations using Hadoop HDFS, MapReduce, and Apache Pig on a large YouTube video dataset.

The dataset contains **749,361 records** distributed across five text files. The project performs data storage, processing, aggregation, and analysis using Hadoop-based technologies.

---

## Dataset

The dataset contains YouTube video information with fields such as:

- Video ID
- Uploader
- Age
- Category
- Length
- Views
- Rating
- Ratings Count
- Comments Count

### Dataset Statistics

| Item | Value |
|---|---:|
| Total Records | 749,361 |
| Input Files | 5 |
| HDFS Blocks | 6 |
| HDFS Replication | 1 |
| Missing Blocks | 0 |
| Corrupt Blocks | 0 |
| Under-replicated Blocks | 0 |

The raw dataset is not included in this repository because of its large size.

---

## Technologies Used

- Java 8
- Hadoop 3.2.4
- HDFS
- YARN
- MapReduce
- Apache Pig 0.18.0
- Windows
- Git/GitHub

---

## HDFS Operations

The dataset was uploaded to:

```text
/bda_final_project/input