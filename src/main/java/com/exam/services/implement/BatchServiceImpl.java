package com.exam.services.implement;

import com.exam.entity.*;
import com.exam.exceptions.NotFoundException;
import com.exam.model.exams.BatchModel;
import com.exam.model.exams.UserModel;
import com.exam.repo.*;
import com.exam.services.BatchService;

import com.exam.services.BatchUserMappingService;
import com.exam.services.ResultService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchService {
        @Autowired
        private BatchRepository batchRepository;
        @Autowired
        private QuizRepository quizRepository;
        @Autowired
	    private UserRepository userRepository;

        @Autowired
        private BatchUserMappingRepo batchUserMappingRepo;

        @Autowired
        private BatchUserMappingService batchUserMappingService;

        @Autowired
        private ResultService resultService;

        @Autowired
        private ResultRepository resultRepository;

        @Override
        public List<BatchModel> getBatches() {
                // List<Batch> batch=batchRepository.findAll();
                List<BatchModel> batchModels = new ArrayList<>();
                /*
                 * batch.forEach(batch1 -> {
                 * // batchModels.add()
                 * });
                 */
                return batchModels;
        }

        @Override
        public Batch addBatch(BatchModel batchModel) {

                Batch batch = new Batch();
                //Quiz retrivequiz=
                batch.setName(batchModel.getName());
                batch.setDate(batchModel.getDate());
                batch.setTime(batchModel.getTime());
                batch.setTrade(batchModel.getTrade());
//                batch.setStatus(batchModel.isStatus());
                batch.setTraineeCount(batchModel.getTraineeCount());
                Quiz quiz = quizRepository.findById(batchModel.getQuizId())
                 .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
                batch.setQuiz(quiz);
//                Optional<Quiz> retriveQuiz = quizRepository.findById(batchModel.getQuizId());
                Batch savedBatch = this.batchRepository.save(batch);

                List<User> retrievedUsers = userRepository.findAllById(batchModel.getUserIds());
                List<BatchUserMapping> batchUserMappings = new ArrayList<>();
                retrievedUsers.forEach(user -> {
                        BatchUserMapping batchUserMapping = new BatchUserMapping();
                        batchUserMapping.setBatch(savedBatch);
                        batchUserMapping.setUser(user);
                        batchUserMapping.setQuizStatus("CREATED");
                        batchUserMappings.add(batchUserMapping);
                });
                List<QuizBatchMapping> quizBatchMappings = new ArrayList<>();

                batchUserMappingRepo.saveAll(batchUserMappings);


//                retriveQuizes.forEach((quiz) -> {
////                        QuizBatchMapping quizBatchMapping = new QuizBatchMapping();
////                        quizBatchMapping.setQuiz(quiz);
//                        quizBatchMapping.setBatch(savedBatch);
//                        quizBatchMappings.add(quizBatchMapping);
//
//                });
//                quizBatchMappingRepo.saveAll(quizBatchMappings);

                // batch.setUser_ids(batchModel.getTraineesIds());
                // batch.setTrainees_couant(batchModel.getTrainees_count());
                //
                // // Fetch the associated quiz
                // Quiz quiz = quizRepository.findById(batchModel.getQuiz_id())
                // .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
                // batch.setQuiz(quiz);

                return savedBatch;
        }

        public Optional<BatchModel> getBatchModelById (long id){
                Optional<Batch> batchData = batchRepository.findById(id);

                if (batchData.isPresent()) {
                        Batch batch = batchData.get();
                        BatchModel batchModel =new BatchModel();
			BeanUtils.copyProperties(batch, batchModel);
			List<UserModel> batchUsers=new ArrayList<>();
			List<BatchUserMapping> batchMappings=batchUserMappingRepo.findByBatch(batch);
			batchMappings.forEach(batchMapping->{
				 		User user=new User();
				 		user=batchMapping.getUser();
                    UserModel userModel=new UserModel(user);

				 		batchUsers.add(userModel);
				 	});

			batchModel.setUserDetails(batchUsers);
                        return Optional.of(batchModel);
				 	
                } else {
                        throw new RuntimeException("Batch not found with id: " + id);
                }

        }

        public Optional<BatchModel> updateBatch(long id, Batch updatedBatch) throws Exception {

                if (this.resultRepository.existsByBatchId(id))
                {
                        throw new Exception("Result exist for the batch");
                }
                else {
                        Optional<Batch> batchData = batchRepository.findById(id);

                        if (batchData.isPresent()) {
                                Batch batch = batchData.get();
                                if (updatedBatch.getName() != null) {
                                        batch.setName(updatedBatch.getName());
                                }
                                if (updatedBatch.getDate() != null) {
                                        batch.setDate(updatedBatch.getDate());
                                }
                                if (updatedBatch.getTime() != null) {
                                        batch.setTime(updatedBatch.getTime());
                                }
        //                        if (updatedBatch.getTraineeCount() != null) {
        //                                batch.setTraineeCount(updatedBatch.getTraineeCount());
        //                        }
        //                        if (updatedBatch.getStatus() != null) {
        //                                batch.setStatus(updatedBatch.getStatus());
        //                        }
                                if (updatedBatch.getTrade() != null) {
                                        batch.setTrade(updatedBatch.getTrade());
                                }

                                batchRepository.save(batch);
                                return getBatchModelById(id);
                        } else {
                                throw new RuntimeException("Batch not found with id: " + id);
                        }
                }
        }
        public Optional<BatchModel> getBatchById(long id) {
                return getBatchModelById(id);
              
        }


        public void deleteBatch(Long batchId) throws Exception {
           if (this.resultRepository.existsByBatchId(batchId))
                {
                        // resultRepository.deleteByBatchId(batchId);
                        resultService.deleteResultByBatchId(batchId);
                       
                }
        this.batchUserMappingService.deleteBatchUserMappingsByBatchId(batchId);
        this.batchRepository.deleteById(batchId);
                
        }

        public void deleteAllBatchesForQuiz(Long quizId) {
                // Fetch all batches associated with the given quiz ID
                try {
                        Optional<Quiz> quiz= quizRepository.findById(quizId);
                        if (quiz.isPresent()) {
                                Quiz quiz1 = quiz.get();

                                List<Batch> batchesToDelete = batchRepository.findAllByQuiz(quiz1);

                                // Efficiently delete batches (if applicable)
                                if (!batchesToDelete.isEmpty()) {
                                        batchesToDelete.forEach(batch -> {
                                                try {
                                                        deleteBatch(batch.getId());
                                                } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                }
                                        });
                                        batchRepository.deleteAllInBatch(batchesToDelete);
                                } else {
                                        // Optional: Log or handle the case where no batches are found
                                        System.out.println("No batches found for quiz ID: " + quizId);
                                }
                        } else {
                                throw new NotFoundException("Quiz not found with id: " + quizId);
                        }
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        throw e;
                }
            }

}
