import { create } from 'zustand';

export const useExamStore = create((set) => ({
  modal: null,
  examList: [],
  targetExam: null,
  openModal: (type) => set({ modal: type }),
  setExamList: (examList) => set({ examList }),
  addNewExam: (newExam) =>
    set((state) => ({ examList: [...state.examList, newExam] })),
  setTargetExam: (targetExam) => set({ targetExam }),
}));
