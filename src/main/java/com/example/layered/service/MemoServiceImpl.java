package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @Override
    public List<MemoResponseDto> findAllMemos() {

        return memoRepository.findAllMemos();
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {

        Memo memo = memoRepository.findMemoById(id);

        if (memo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new MemoResponseDto(memo);
    }

    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {

        Memo memo = memoRepository.findMemoById(id);

        if (memo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and contents cannot be null");
        }

        memo.update(title, contents);

        return new MemoResponseDto(memo);
    }

    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {

        // memo 조회
        Memo memo = memoRepository.findMemoById(id);

        if (memo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        if (title == null || contents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and contents cannot be null");
        }

        memo.updateTitle(title);


        return new MemoResponseDto(memo);
    }
}
