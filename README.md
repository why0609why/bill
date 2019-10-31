# bill
一个SpringBoot的Restful风格的前后端半分离的项目，数据访问层使用Mybatis，视图渲染使用Thymeleaf，支持模糊搜索，条件搜索

一个很好的练习Resultful的项目~~~

# 有关这个demo的几个点
## 关于请求的处理
1. 查询全部和按条件查询的controller是一个方法，它们都有一个形参，这个形参为要查询的对象(比如这个demo拿provider这个对象举例)，这个对象就是从前台接受的条件，当只是查询全部的某个对象时(要返回的结果是List<Provider>)，那么形参为空，如果有根据这个对象的属性来按照条件查询时，那么这个provider对象中就会有被查询某一个或者两个属性。在mybatis里面通过<if>条件来查询，比如下面这个xml中的sql语句就实现了查询全部、按provider的名字查询的功能
``` xml
<select id="getProviders" resultType="Provider">
        select * from provider where 1=1
        <if test="providerName != null and providerName != ''">
            <!--${} 用于字符串拼接-->
            and providerName like '%${providerName}%'
        </if>
    </select>
```
2. 更新和按照id查询的controller也是同一个方法，可以想象，更新一个provider和按照id查询一个provider需要的都是一个provider的id，其在查询的过程中也是需要获取到id为指定id的全部的provider对象。所以这就明了了，下面是处理查询请求和更新请求的那个方法。发送更新请求的url是`th:href="@{/provider/} + ${p.pid}" `,而发送根据id查询的请求是`th:href="@{/provider/} + ${p.pid} +'?type=update'"`,两者的请求都会被下面那一个方法接收，只不过最终去往的页面不同，前者去的是更新页面，后者去的是展示单个的页面，而且对于更新来说，最终的更新页面也是要有提交的，如果说在更新页面做展示的时候就使用对象来展示的话，那么更新提交的时候就可以直接封装好要提交的对象了，很方便。
``` java
/**
     * type = null 进入查看详情页面view.html，
     * type=update 则是进入update.html
     * @param pid 供应商id
     * @param type
     * @param map
     * @return
     */
    @GetMapping("/provider/{pid}")
    public String view(@PathVariable("pid") Integer pid,
                       @RequestParam(value="type", defaultValue = "view") String type,
                       Map<String, Object> map) {

        //首先根据id获取到指定的provider的对象
        Provider provider = providerMapper.getProviderByPid(pid);

        //将或得到的数据放到request作用域中，以使thymeleaf渲染
        map.put("provider", provider);

        // type = null 则进入view.html， type=update 则是进入update.html
        return "provider/" + type;
    }
```
3. 至于增的请求不必说，表单的提交方法一定是post。而且注意输入项<input>中的name属性要和被封装的对象的属性的名字是一样的，这样才能保证封装不出问题
4. 删除的话，可以把删除的请求封装到一个表单里，在表单设置`<input type="hidden" name="_method" value="delete">`,然后可以在点击删除的请求的时候提交表单就可以了。
