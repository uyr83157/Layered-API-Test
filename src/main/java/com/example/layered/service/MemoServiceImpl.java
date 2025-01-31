package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

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
        return memoRepository.saveMemo(memo);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {

        return memoRepository.findAllMemos();
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {

        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);


        return new MemoResponseDto(memo);
    }

    @Transactional
    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {


        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and contents cannot be null");
        }

        int updatedRow = memoRepository.updateMemo(id, title, contents);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }


        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);

        return new MemoResponseDto(memo);
    }

    @Transactional
    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {

        if (title == null || contents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and contents cannot be null");
        }

        int updatedRow = memoRepository.updateTitle(id, title);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);


        return new MemoResponseDto(memo);

    }

    @Override
    public void deleteMemo(Long id) {

        int deletedRow = memoRepository.deleteMemo(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

    }
}
