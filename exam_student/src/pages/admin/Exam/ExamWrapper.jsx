import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { getExams } from '~/apis';
import { Backdrop } from '~/components';
import { useExamStore } from '~/store';
import DetailExam from './DetailExam';
import UpdateExam from './UpdateExam';
import DeleteExam from './DeleteExam';
import FormCreateExam from './FormCreateExam';
import ExamList from './ExamList';

const ModalFormObj = {
  ['view']: (
    <Backdrop opacity={0.25}>
      <DetailExam />
    </Backdrop>
  ),
  ['edit']: (
    <Backdrop opacity={0.25}>
      <UpdateExam />
    </Backdrop>
  ),
  ['delete']: <DeleteExam />,
};

function ExamWrapper() {
  const { setExamList, modal, targetExam } = useExamStore((state) => {
    console.log('STATE', state);
    return state;
  });

  useEffect(() => {
    (async () => {
      try {
        const listExam = await getExams();
        console.log('GET o day', listExam);
        setExamList(listExam);
      } catch (error) {
        toast.error(error.message, { toastId: 'get_exam' });
      }
    })();
  }, [setExamList]);

  return (
    <>
      <div className="w-full ">
        <FormCreateExam />
      </div>
      <div className="mt-4 w-full h-[calc(100%-4rem)]">
        <ExamList />
      </div>

      {targetExam && modal && ModalFormObj[modal]}
    </>
  );
}

export default ExamWrapper;
