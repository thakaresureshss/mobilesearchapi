package com.mobile.search.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
class CacheConfig {
  @Bean
  Config config() {
    return new Config().setInstanceName("hazelcast-instance")
        .addMapConfig(new MapConfig().setName("mobiles")
            .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
            .setEvictionPolicy(EvictionPolicy.LRU).setTimeToLiveSeconds(2000));
  }
}
