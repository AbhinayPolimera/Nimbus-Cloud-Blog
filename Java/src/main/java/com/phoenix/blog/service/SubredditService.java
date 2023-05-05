package com.phoenix.blog.service;

import com.phoenix.blog.dto.CategoryDto;
import com.phoenix.blog.exceptions.SpringRedditException;
import com.phoenix.blog.mapper.SubredditMapper;
import com.phoenix.blog.dao.SubredditRepository;
import com.phoenix.blog.model.Subreddit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private  SubredditRepository subredditRepository;
    private  SubredditMapper subredditMapper;

    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(categoryDto));
        categoryDto.setId(save.getId());
        return categoryDto;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public CategoryDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
