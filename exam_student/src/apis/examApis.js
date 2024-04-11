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
      const a = (await axiosClient.get('/quiz/')).data;
      console.log("NHU", a);
      // return (await axiosClient.get('/quiz/')).data;
    } catch (error) {
      console.error(error);
      throw new Error('Failed to get exam');
    }
  };