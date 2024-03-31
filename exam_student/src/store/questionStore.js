import { create } from 'zustand';

export const useQuestionStore = create((set) => ({
  isFetching: false,
  isEditing: false,
  questionList: [],
  targetQuestion: null,
  setIsFetching: (isFetching) => set({ isFetching }),
  setIsEditing: (isEditing) => set({ isEditing }),
  setQuestionList: (questionList) => set({ questionList }),
  addNewQuestion: (newQuestion) =>
    set((state) => ({ questionList: [...state.questionList, newQuestion] })),
  setTargetQuestion: (targetQuestion) => set({ targetQuestion }),
}));
