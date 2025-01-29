package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.stereotype.Service;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto memoRequestDto) {

        // 요청 받은 데이터로 MEMO 객체 생성
        Memo memo = new Memo(memoRequestDto.getTitle(), memoRequestDto.getContents());

        // DB 저장
        Memo savedMemo = memoRepository.saveMemo(memo);


        return new MemoResponseDto(savedMemo);
    }
}
