package com.example.demo.Service;

import com.example.demo.Entity.Blog;
import java.util.List;

public interface BlogService {
    List<Blog> findAll();

    Blog findById(Long id);

    void save(Blog blog);

    void delete(Long id);
}
