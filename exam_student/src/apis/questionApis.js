import { axiosClient } from './axiosClient';

export const getQuestions = async () => {
  try {
    return (await axiosClient.get('/question/')).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to fetch questions');
  }
};

export const getQuestionTypes = async () => {
  try {
    return (await axiosClient.get('/question-type/')).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to fetch question types');
  }
};
