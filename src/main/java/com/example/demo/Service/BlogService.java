package com.example.demo.Service;

import com.example.demo.Entity.Blog;
import java.util.List;

public interface BlogService {
    void save(Blog blog);
    List<Blog> findAll();
    Blog findById(Long id);
    void delete(Long id);
}
