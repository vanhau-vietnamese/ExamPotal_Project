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

export const createQuestion = async (data) => {
  try {
    return (await axiosClient.post('/question/add', data)).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to create question');
  }
};

export const editQuestion = async (id, body) => {
  try {
    return (await axiosClient.put(`/question/edit/${id}`, body)).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to edit question');
  }
};

export const deleteQuestion = async (id) => {
  try {
    return (await axiosClient.put(`/question/delete/${id}`)).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to delete question');
  }
};

export const getQuesOfCategory = async (id) => {
  try {
    return (await axiosClient.get(`/question/category/${id}`)).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to delete exam');
  }
};
