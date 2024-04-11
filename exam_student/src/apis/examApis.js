import { axiosClient } from './axiosClient';

export const createExam = async (data) => {
    try {
      return (await axiosClient.post('/quiz/add', data)).data;
    } catch (error) {
      console.error(error);
      throw new Error('Failed to create exam');
    }
  };

  export const getExams = async () => {
    try {
      return (await axiosClient.get('/quiz/')).data;
    } catch (error) {
      console.error(error);
      throw new Error('Failed to get exam');
    }
  };

  export const getQuesOfQuiz = async (id) => {
    try {
      return (await axiosClient.get(`/question/quiz/${id}`)).data;
    } catch (error) {
      console.error(error);
      throw new Error('Failed to get exam');
    }
  };

  export const updateQuiz = async (id, body) => {
    try {
      return (await axiosClient.put(`/quiz/update/${id}`, body)).data;
    } catch (error) {
      console.error(error);
      throw new Error('Failed to get exam');
    }
  };