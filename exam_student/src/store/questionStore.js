import { create } from 'zustand';

export const useQuestionStore = create((set) => ({
  loading: false,
  isEditing: false,
  questionList: [],
  targetQuestion: null,
  setLoading: (loading) => set({ loading }),
  setIsEditing: (isEditing) => set({ isEditing }),
  setQuestionList: (questionList) => set({ questionList }),
  setTargetQuestion: (targetQuestion) => set({ targetQuestion }),
}));
