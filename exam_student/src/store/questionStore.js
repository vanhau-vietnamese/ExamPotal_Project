import { create } from 'zustand';

export const useQuestionStore = create((set) => ({
  isEditing: false,
  questionTypes: [],
  questionList: [],
  targetQuestion: null,
  setQuestionType: (questionTypes) => set({ questionTypes }),
  setIsEditing: (isEditing) => set({ isEditing }),
  setQuestionList: (questionList) => set({ questionList }),
  addNewQuestion: (newQuestion) =>
    set((state) => ({ questionList: [...state.questionList, newQuestion] })),
  setTargetQuestion: (targetQuestion) => set({ targetQuestion }),
}));
