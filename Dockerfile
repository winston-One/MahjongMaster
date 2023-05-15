FROM openjdk:8
WORKDIR /HiMahjong
ADD ./target/HiMahjong-1.0.jar /HiSchool
EXPOSE 9790
ENTRYPOINT java -jar /HiMahjong/HiMahjong.jar
CMD ["HiMahjong-1.0.jar"]