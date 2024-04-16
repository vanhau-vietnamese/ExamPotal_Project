import { axiosClient } from './axiosClient';

export const getAllCategories = async () => {
  try {
    return (await axiosClient.get('/category/')).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to fetch categories');
  }
};

export const deleteCategory = async (id) => {
  try {
    return (await axiosClient.delete(`/category/delete/${id}`)).data;
  } catch (error) {
    console.error(error);
    throw new Error('Failed to delete category');
  }
};
