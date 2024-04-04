import { Button, EditorViewer } from '~/components';
import { OptionItem } from '~/layouts/components';
import { useQuestionStore } from '~/store';

function ViewDetailQuestion() {
  const { targetQuestion, setTargetQuestion } = useQuestionStore((state) => state);

  return (
    <div className="w-full h-full mx-auto max-w-[800px] p-10">
      <form className="w-full h-full bg-white rounded-lg flex flex-col justify-between">
        {targetQuestion && (
          <div key={targetQuestion.id} className="p-3 flex flex-col">
            <h1 className="m-5 justify-between">Chi tiết câu hỏi: {targetQuestion.id} </h1>
            <h3 className="text-[16px] font-semibold ml-5 mt-3">
              Loại câu hỏi: {targetQuestion.questionType.displayName}
            </h3>
            <div className="border border-gray bg-white m-1 rounded-md shadow-md w-80%">
              <div className="m-5">
                <EditorViewer content={targetQuestion.content} />
              </div>

              <ul className="ml-4 space-y-2">
                <OptionItem options={targetQuestion.answers} />
              </ul>
            </div>
          </div>
        )}

        <Button
          className="px-6 py-2 m-5 w-[100px] text-sm !border !border-danger text-danger hover:bg-danger hover:bg-opacity-5"
          onClick={() => setTargetQuestion(null)}
        >
          Thoát
        </Button>
      </form>
    </div>
  );
}

export default ViewDetailQuestion;
