import FeatureQuestion from './FeatureQuestion';
import { OptionItem } from '~/layouts/components';
import { useEffect } from 'react';
import { EditorContent, useEditor } from '@tiptap/react';
import PropTypes from 'prop-types';
import extensions from '~/components/TextEditor/extensions';

function QuestionItem({ item }) {
  console.log('BBBBB', item);
  //
  const editor = useEditor({
    editable: false,
    content: `abc`,
    extensions,
  });

  useEffect(() => {
    if (!editor) {
      return undefined;
    }
  }, [editor, item]);

  if (!editor) {
    return null;
  }

  return (
    <div className="p-3">
      <div className="border border-gray bg-white m-1 rounded-md shadow-md w-full">
        <EditorContent editor={editor} />
        <ul className="ml-4 space-y-2">
          <li>
            <OptionItem options={item.answers} />
            <FeatureQuestion></FeatureQuestion>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default QuestionItem;

QuestionItem.propTypes = {
  item: PropTypes.object.isRequired,
};
