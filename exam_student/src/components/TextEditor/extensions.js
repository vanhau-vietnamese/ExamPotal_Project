import Bold from '@tiptap/extension-bold';
import Code from '@tiptap/extension-code';
import Document from '@tiptap/extension-document';
import Heading from '@tiptap/extension-heading';
import History from '@tiptap/extension-history';
import Italic from '@tiptap/extension-italic';
import ListItem from '@tiptap/extension-list-item';
import OrderedList from '@tiptap/extension-ordered-list';
import Paragraph from '@tiptap/extension-paragraph';
import Strike from '@tiptap/extension-strike';
import Text from '@tiptap/extension-text';
import TextAlign from '@tiptap/extension-text-align';
import Underline from '@tiptap/extension-underline';

export default [
  Heading.configure({
    levels: [1, 2, 3],
  }),
  Document,
  History,
  Paragraph,
  Text,
  TextAlign.configure({
    types: ['heading', 'paragraph'],
  }),
  Bold,
  Underline,
  Italic,
  Strike,
  Code,
  ListItem,
  OrderedList,
];
