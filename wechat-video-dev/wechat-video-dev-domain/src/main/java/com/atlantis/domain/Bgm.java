package com.atlantis.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;

@ApiModel(value = "Bgm", description = "背景音乐实体类")
public class Bgm {
    @ApiModelProperty(hidden = true)
    @Id
    private String id;

    @ApiModelProperty(value = "作者", name = "author")
    private String author; // 作者

    @ApiModelProperty(value = "歌曲名", name = "name")
    private String name; // 歌曲名

    @ApiModelProperty(value = "资源路径", name = "path")
    private String path; // 资源路径

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}