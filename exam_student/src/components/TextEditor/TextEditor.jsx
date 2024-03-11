import { EditorContent, useEditor } from '@tiptap/react';
import PropTypes from 'prop-types';
import { useCallback } from 'react';
import * as Icons from './Icons';
import ToolBarButton from './ToolBarButton';
import extensions from './extensions';

TextEditor.propTypes = {
  data: PropTypes.string,
  onChange: PropTypes.func,
};

export default function TextEditor({ data, onChange }) {
  const editor = useEditor({
    extensions,
    content: data,
    onUpdate: ({ editor }) => onChange(editor.getJSON()),
  });

  const toggleHeading = useCallback(
    (level) => {
      editor.chain().focus().toggleHeading({ level }).run();
    },
    [editor]
  );

  const toggleBold = useCallback(() => {
    editor.chain().focus().toggleBold().run();
  }, [editor]);

  const toggleUnderline = useCallback(() => {
    editor.chain().focus().toggleUnderline().run();
  }, [editor]);

  const toggleItalic = useCallback(() => {
    editor.chain().focus().toggleItalic().run();
  }, [editor]);

  const toggleTextAlign = useCallback(
    (align = 'left') => {
      editor.chain().focus().setTextAlign(align).run();
    },
    [editor]
  );

  const toggleStrike = useCallback(() => {
    editor.chain().focus().toggleStrike().run();
  }, [editor]);

  const toggleCode = useCallback(() => {
    editor.chain().focus().toggleCode().run();
  }, [editor]);

  const toggleListBullet = useCallback(() => {
    editor.chain().focus().toggleBulletList().run();
  }, [editor]);

  const toggleOrderedList = useCallback(() => {
    editor.chain().focus().toggleOrderedList().run();
  }, [editor]);

  if (!editor) {
    return null;
  }

  return (
    <div className="relative w-full mb-12">
      <div className="absolute top-[2px] left-[2px] z-10 flex items-center gap-2 w-[calc(100%-4px)] h-10 m-0 px-2 py-0 rounded-ss rounded-se bg-strike">
        <ToolBarButton
          icon={<Icons.HeadingOne />}
          isActive={editor.isActive('heading', { level: 1 })}
          onClick={() => toggleHeading(1)}
        />

        <ToolBarButton
          icon={<Icons.HeadingTwo />}
          isActive={editor.isActive('heading', { level: 2 })}
          onClick={() => toggleHeading(2)}
        />

        <ToolBarButton
          icon={<Icons.HeadingThree />}
          isActive={editor.isActive('heading', { level: 3 })}
          onClick={() => toggleHeading(3)}
        />

        <div className="w-[1px] h-full py-3">
          <div className="w-full h-full border-r border-slate-400 rounded-md" />
        </div>

        <ToolBarButton
          icon={<Icons.Bold />}
          isActive={editor.isActive('bold')}
          onClick={toggleBold}
        />

        <ToolBarButton
          icon={<Icons.Underline />}
          isActive={editor.isActive('underline')}
          onClick={toggleUnderline}
        />

        <ToolBarButton
          icon={<Icons.Italic />}
          isActive={editor.isActive('italic')}
          onClick={toggleItalic}
        />

        <div className="w-[1px] h-full py-3">
          <div className="w-full h-full border-r border-slate-400 rounded-md" />
        </div>

        <ToolBarButton
          icon={<Icons.TextAlignLeft />}
          isActive={editor.isActive({ textAlign: 'left' })}
          onClick={() => toggleTextAlign('left')}
        />

        <ToolBarButton
          icon={<Icons.TextAlignCenter />}
          isActive={editor.isActive({ textAlign: 'center' })}
          onClick={() => toggleTextAlign('center')}
        />

        <ToolBarButton
          icon={<Icons.TextAlignRight />}
          isActive={editor.isActive({ textAlign: 'right' })}
          onClick={() => toggleTextAlign('right')}
        />

        <div className="w-[1px] h-full py-3">
          <div className="w-full h-full border-r border-slate-400 rounded-md" />
        </div>

        <ToolBarButton
          icon={<Icons.Strikethrough />}
          isActive={editor.isActive('strike')}
          onClick={toggleStrike}
        />

        <ToolBarButton
          icon={<Icons.BulletList />}
          isActive={editor.isActive('bulletList')}
          onClick={toggleListBullet}
        />
        <ToolBarButton
          icon={<Icons.OrderedList />}
          isActive={editor.isActive('orderedList')}
          onClick={toggleOrderedList}
        />

        <ToolBarButton
          icon={<Icons.Code />}
          isActive={editor.isActive('code')}
          onClick={toggleCode}
        />
      </div>

      <EditorContent editor={editor} />
    </div>
  );
}
