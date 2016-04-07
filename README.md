# GridViewLayout
提供一个高效、轻量、灵活的Grid布局方案，用以替代安卓官方的GridView。

### GridViewLayout能做什么
提供单行、单列、多行多列三种模式。用法与日常的ListView、RecyclerView等类似。通过适配器来进行数据绑定与UI填充。目的是解决Android日常开发中，大量需要Grid布局的情景。GridView我们知道，是与ListView同级的，带有滚动和复用功能的重级控件。如果一个ListView中为了满足某个需要Grid布局的需求，而嵌入一个GridView，是非常浪费资源与消耗性能的。GridViewLayout本质上是一个LinearLayout，相对于Android官方提供的GridView，更加简单易用，轻量灵活。

### GridViewLayout不能做什么
GridViewLayout只适用于少量单元格Grid布局的需求的页面，无法滚动，无法单元格复用。

### GridViewLayout怎么用
**第一步**
```groovy
compile 'com.m4399:gridviewlayout:1.0.0'
```
**第二步**
非常的简单，请戳代码，附上效果图：

<p align="center" >
  <img src="https://github.com/aqiansunboy/GridViewLayout/blob/master/Screenshot.png?raw=true" alt="GridViewLayout" title="GridViewLayout">
</p>
