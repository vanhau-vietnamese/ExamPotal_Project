import PropTypes from 'prop-types';
import { useState } from 'react';
import { useController } from 'react-hook-form';
import Icons from '~/assets/icons';
import { Button } from '~/components';
import AnswerItem from './AnswerItem';

const InputType = {
  ['single_choice']: 'radio',
  ['multiple_choice']: 'checkbox',
};

export default function AnswersCreate({
  control,
  type = 'single_choice',
  name,
  label,
  defaultValue,
}) {
  const { field } = useController({
    control,
    name,
    shouldUnregister: true,
    defaultValue: defaultValue || [],
  });

  const [answers, setAnswers] = useState(field.value);

  const handleChangeAnswer = (index, value) => {
    const newAnswers = [...answers];
    newAnswers[index] = value;
    setAnswers(newAnswers);
    field.onChange(newAnswers);
  };

  const handleDeleteAnswer = (index) => {
    if (answers.length === 1) return;
    const newAnswers = answers.filter((_, i) => i !== index);
    setAnswers(newAnswers);
    field.onChange(newAnswers);
  };

  return (
    <div className="w-full mt-5">
      <div className="flex items-center w-full justify-between mb-2">
        <label className="block p-1 text-sm font-bold text-icon">
          {label}
          <strong className="text-error"> *</strong>
        </label>

        <Button
          type="button"
          className="p-1 text-sm text-primary flex items-center gap-1 hover:bg-primary hover:bg-opacity-10"
          onClick={() => {
            setAnswers([...answers, '']);
            field.onChange([...answers, '']);
          }}
        >
          <Icons.Plus />
          <span>Thêm đáp án</span>
        </Button>
      </div>
      <div className="flex flex-col gap-4">
        {answers.map((answer, index) => (
          <AnswerItem
            key={index}
            type={InputType[type]}
            value={answer}
            deletable={index !== 0}
            onChange={(value) => handleChangeAnswer(index, value)}
            onDelete={() => handleDeleteAnswer(index)}
          />
        ))}
      </div>
    </div>
  );
}

AnswersCreate.propTypes = {
  control: PropTypes.object,
  type: PropTypes.string,
  name: PropTypes.string,
  label: PropTypes.string,
  defaultValue: PropTypes.array,
};
