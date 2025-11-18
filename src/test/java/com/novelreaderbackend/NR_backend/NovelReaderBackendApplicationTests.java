package com.novelreaderbackend.NR_backend;

import com.novelreaderbackend.NR_backend.tools.regionWebURL;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

@SpringBootTest
class NovelReaderBackendApplicationTests {
	private static final SecureRandom random = new SecureRandom();
	@Test
	void generateJsonpCallback(){
		// 1. 生成一个 20~22 位的数字字符串（模拟 jQuery 的 uniqueId）
		// 方法：取当前纳秒 + 随机数，去掉非数字字符（其实全是数字）
		long base = System.nanoTime(); // 高精度时间
		long rand = Math.abs(random.nextLong() % 10000000000L); // 10位随机数
		String uniquePart = String.valueOf(base + rand).replaceAll("\\D", ""); // 确保纯数字

		// 如果太短，补足到至少 20 位
		while (uniquePart.length() < 20) {
			uniquePart += Math.abs(random.nextInt() % 10);
		}
		if (uniquePart.length() > 22) {
			uniquePart = uniquePart.substring(0, 22);
		}

		// 2. 当前毫秒时间戳
		long timestamp = System.currentTimeMillis();

		String jsonpCallback = "jQuery" + uniquePart + "_" + timestamp;
		System.out.println(jsonpCallback);
	}

	@Test
	void generateRegionWebURL(){
		System.out.println(regionWebURL.getRegionWebURL(10, "https://www.qidian.com/", null));
	}
}
