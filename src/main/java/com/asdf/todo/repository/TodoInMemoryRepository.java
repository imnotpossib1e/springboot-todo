package com.asdf.todo.repository;

import com.asdf.todo.model.Todo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class TodoInMemoryRepository {
    /**
     * Todo 데이터를 DB 대신 메모리에 저장하는것 공간을 만든 것
     * new HashMap<>() -> DB 대신 RAM에 저장
     */
    private final Map<Long, Todo> todoMap = new HashMap<>();

    /** ID를 1씩 증가시키기 위한 숫자 생성기 **/
    private final AtomicLong counter = new AtomicLong();

    /** 저장된 Todo 전체를 리스트로 꺼내서 반환하는 함수 **/
    public List<Todo> findAll() {
        // 그냥 return values() 하면 외부에서 수정 가능하기 때문에 복사해서 반환한다.
        return new ArrayList<>(todoMap.values());
    }

    /** id로 Todo 하나를 찾아서 반환하는 조회 함수 **/
    public Todo findById(Long id){
        Todo todo = todoMap.get(id);

        // 예외처리
        if(todo == null){
            throw new RuntimeException("Todo not found");
        }
        return todo;
    }

    /** Todo를 저장 또는 수정하는 메서드로 id가 없으면 새로 생성, 있으면 수정해서 map에 저장 **/
    public Todo save(Todo todo) {
        if (todo.getId() == null){
            todo.setId(counter.incrementAndGet());
        }
        todoMap.put(todo.getId(), todo);
        return todo;
    }

    /** Todo를 삭제하는 메서드 **/
    public void deleteById(Long id){
        todoMap.remove(id);
    }
}
