package com.exam.repo;

import com.exam.entity.Batch;

import com.exam.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchRepository extends JpaRepository<Batch,Long>{

    //	public List<Quiz> findByCategory(Category cat);
//    public default List<Batch> findByQuiz(Quiz quiz) {
//        return null;
//    }

    List<Batch> findByQuiz(Long quizId);

     List<Batch> findAllByQuiz(Quiz quiz);


    @Override
    default List<Batch> findAll() {
        return null;
    }
    
    @Override
    default List<Batch> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    default <S extends Batch> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    default Batch getById(Long aLong) {
        return null;
    }


}
