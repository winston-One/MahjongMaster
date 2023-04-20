//package com.queshen.config.cache;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.github.benmanes.caffeine.cache.LoadingCache;
//import com.haotongxue.dto.StrategyPageDto;
//import com.haotongxue.entity.vo.StrategyComprehensiveVO;
//import com.haotongxue.service.strategy.SelectedCourseService;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author winston
// * @create 2023/3/2 15:51
// * @Description: Man can conquer nature
// List<StrategyComprehensiveVO> courseCache = indexCourseCache.get(
//                new SelectedCourseCache.IndexCourseVoKey(queryObj,"selectedCourse"));
// @Resource(name = "indexCourseCache")
//    LoadingCache<SelectedCourseCache.IndexCourseVoKey, List<StrategyComprehensiveVO>> indexCourseCache;
// **/
//@Configuration
//public class SelectedCourseCache {
//
//    SelectedCourseService selectedCourseService;
//
//    /**
//     * 首页公选课缓存
//     * IndexCourseVoKey是缓存的键
//     * List<StrategyComprehensiveVO>是缓存的值
//     * 使用的是同步加载的方法
//     */
//    @Bean("indexCourseCache")
//    public LoadingCache<IndexCourseVoKey, List<StrategyComprehensiveVO>> getCache() {
//        // 初始化缓存
//        return Caffeine.newBuilder()
//                .expireAfterWrite(Duration.ofMinutes(60))
//                .refreshAfterWrite(1, TimeUnit.SECONDS)
//                .build(key -> selectedCourseService.getCourseByComment(key.strategyPageDto));
//    }
//
//    public SelectedCourseCache(SelectedCourseService selectedCourseService ) {
//        this.selectedCourseService = selectedCourseService;
//    }
//
//    @AllArgsConstructor
//    public static class IndexCourseVoKey {
//
//        public StrategyPageDto strategyPageDto;
//
//        public String indexType;
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass())
//                return false;
//            IndexCourseVoKey that = (IndexCourseVoKey) o;
//            return strategyPageDto.equals(that.strategyPageDto) && indexType.equals(that.indexType);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(strategyPageDto,indexType);
//        }
//    }
//}
