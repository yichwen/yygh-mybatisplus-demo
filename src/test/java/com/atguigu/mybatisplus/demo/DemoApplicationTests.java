package com.atguigu.mybatisplus.demo;

import com.atguigu.mybatisplus.demo.entity.User;
import com.atguigu.mybatisplus.demo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		List<User> users = userMapper.selectList(null);
		System.out.println(users);
	}

	// add
	@Test
	public void testAdd() {
		User user = new User();
		user.setName("Wang Wu");
		user.setAge(20);
		user.setEmail("1243@qq.com");
		// 返回的值是影响行数
		int insert = userMapper.insert(user);
		System.out.println(insert);
	}

	@Test
	public void testUpdate() {
		User user = new User();
		user.setId(1384070404083052546L);
		user.setName("Kobe");
		int update = userMapper.updateById(user);
		System.out.println(update);
	}

	@Test
	public void testOptimisticLocker() {
		// query
		User user = userMapper.selectById(1384077052856041474L);
		// update
		user.setName("Zhang San");
		userMapper.updateById(user);
	}

	@Test
	public void testSelectBatchIds() {
		List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
		System.out.println(users);
	}

	@Test
	public void testSelectByMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Jack");
		map.put("age", 20);
		List<User> users = userMapper.selectByMap(map);
		System.out.println(users)	;
	}

	// 分页查询，需要分页插件
	@Test
	public void testSelectPage() {
		Page<User> page = new Page<>(1, 3);
		Page<User> userPage = userMapper.selectPage(page, null);
		// 返回对象得到分页所有数据
		long pages = userPage.getPages();	// total pages 总页数
		long current = userPage.getCurrent();	// current page 当前页数
		List<User> records = userPage.getRecords();	// user collection 查询数据集合
		long total = userPage.getTotal();  // total users 总记录数
		boolean hasNext = userPage.hasNext(); // 下一页
		boolean hasPrevious = userPage.hasPrevious(); // 上一页

		System.out.println(pages);
		System.out.println(current);
		System.out.println(records);
		System.out.println(total);
		System.out.println(hasNext);
		System.out.println(hasPrevious);
	}

	// 逻辑删除
	// 在表添加字段，作为逻辑删除标志，每次删除时候，修改标志位
	// 0 没有删除
	// 1 删除
	@Test
	public void testDeleteById() {
		int deleted = userMapper.deleteById(1384132282301808642L);
		System.out.println(deleted);
	}

	@Test
	public void testDeleteBatchIds() {
		int deleted = userMapper.deleteBatchIds(Arrays.asList(2, 3, 4));
		System.out.println(deleted);
	}

	@Test
	public void testDeleteByMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Wang Wu");
		int deleted = userMapper.deleteByMap(map);
		System.out.println(deleted);
	}

	@Test
	public void testSelect() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		// ge, gt, le, lt
//		queryWrapper.ge("age", 21);

		// eq, ne
//		queryWrapper.eq("name", "Tom");

		// between (inclusive), notBetween
//		queryWrapper.between("age", 24, 28);

		// like, notLike, likeLeft "%xx", likeRight "xx%"
//		queryWrapper.like("name", "Zhang");

		// orderBy, orderByDesc, orderByAsc
		queryWrapper.orderByDesc("id");

		List<User> users = userMapper.selectList(queryWrapper);
		System.out.println(users);
	}

}
