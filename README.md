# GridViewLayout
提供一个高效、轻量、灵活的Grid布局方案，用以替代安卓官方的GridView。
<p align="center" >
  <img src="https://github.com/aqiansunboy/GridViewLayout/blob/master/Screenshot.png?raw=true" alt="GridViewLayout" title="GridViewLayout">
</p>
### GridViewLayout能做什么
提供单行、单列、多行多列三种模式。用法与日常的ListView、RecyclerView等类似。通过适配器来进行数据绑定与UI填充。目的是解决Android日常开发中，大量需要Grid布局的情景。GridView我们知道，是与ListView同级的，带有滚动和复用功能的重级控件。如果一个ListView中为了满足某个需要Grid布局的需求，而嵌入一个GridView，是非常浪费资源与消耗性能的。GridViewLayout本质上是一个LinearLayout，相对于Android官方提供的GridView，更加简单易用，轻量灵活。

### GridViewLayout不能做什么
GridViewLayout只适用于少量单元格Grid布局的需求的页面，无法滚动，无法单元格复用。

### GridViewLayout怎么用
**第一步**
```groovy
compile 'com.m4399:gridviewlayout:1.0.0.1'
```
*<a href='http://search.maven.org/#search%7Cga%7C1%7CGridViewwLayout'><img src='http://img.shields.io/maven-central/v/io.reactivex/rxandroid.svg'></a>

**第二步**
非常的简单，开始写代码吧~
- 单行模式
```xml
<com.m4399.gridviewlayout.GridViewLayout
            android:id="@+id/singleLineGrid"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:gridLineMode="singleLine"
            app:numColumns="4"
            app:horizontalSpacing="8dp"
            />
```

- 单列模式
```xml
<com.m4399.gridviewlayout.GridViewLayout
            android:id="@+id/singleColumnGrid"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:gridLineMode="singleColumn"
            app:numRows="4"
            app:verticalSpacing="8dp"
            />
```

- 多行多列
```xml
<com.m4399.gridviewlayout.GridViewLayout
            android:id="@+id/multiRowColumnGrid"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:gridLineMode="multiLine"
            app:numColumns="2"
            app:verticalSpacing="8dp"
            app:horizontalSpacing="8dp"
            />
```

###LICENSE

    Copyright 2016 The GridViewLayout authors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
