import PropTypes from 'prop-types';
import { useId } from 'react';
import Icons from '~/assets/icons';
import { Button } from '~/components';

const InputTypeStyle = {
  ['radio']: 'rounded-full',
  ['checkbox']: 'rounded-md',
};

export default function AnswerItem({ type, value, deletable, onChange, onDelete }) {
  const id = useId();
  return (
    <div className="inline-flex items-center gap-2">
      <label className="relative flex items-center p-3 cursor-pointer" htmlFor={id}>
        <input
          id={id}
          name="answer"
          type={type}
          checked={value.isCorrect}
          onChange={(e) => onChange({ content: value?.content, isCorrect: e.target.checked })}
          className={`before:content[''] peer relative h-5 w-5 cursor-pointer appearance-none border-2 border-strike checked:border-primary transition-all hover:border-primary ${InputTypeStyle[type]}`}
        />
        <span
          className={`absolute text-white bg-primary transition-opacity opacity-0 pointer-events-none top-2/4 left-2/4 -translate-y-2/4 -translate-x-2/4 peer-checked:opacity-100`}
        >
          <svg
            className="w-4 h-4"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={4}
              d="M5 13l4 4L19 7"
            ></path>
          </svg>
        </span>
      </label>
      <input
        type="text"
        className="flex-1 w-full px-4 py-2 border outline-none transition-all placeholder:font-medium text-sm text-[#3b3e66] font-semibold rounded-md hover:border-secondary focus:border-secondary"
        placeholder="Nhập đáp án..."
        value={value.content}
        onChange={(e) =>
          onChange({ content: e.target.value, isCorrect: value?.isCorrect || false })
        }
      />
      <Button
        className="p-2 text-danger hover:bg-danger hover:bg-opacity-10 disabled:hover:bg-transparent"
        onClick={onDelete}
        disable={!deletable}
      >
        <Icons.Trash />
      </Button>
    </div>
  );
}

AnswerItem.propTypes = {
  type: PropTypes.string,
  onChange: PropTypes.func,
  deletable: PropTypes.bool,
  onDelete: PropTypes.func,
  value: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.shape({
      content: PropTypes.string,
      isCorrect: PropTypes.bool,
    }),
  ]),
};
