FROM anapsix/alpine-java
MAINTAINER planyourlife
COPY your.jar /home/your.jar
CMD ["java","-jar","/path/to/your.jar"]