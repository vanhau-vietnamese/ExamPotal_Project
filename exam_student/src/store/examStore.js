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
  updateExam: (targetExam) =>
    set((state) => {
      const indexQues = state.examList.findIndex((exam) => {
        return exam.id === targetExam.id;
      });
      if (indexQues === -1) {
        return state;
      }
      const cloneList = [...state.examList];
      cloneList[indexQues] = targetExam;
      return { examList: cloneList };
    }),
}));
