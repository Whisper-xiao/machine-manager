package com.sduept.dragon.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sduept.dragon.manager.entity.Note;
import com.sduept.dragon.manager.mapper.NoteMapper;
import com.sduept.dragon.manager.service.NoteService;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {
}
