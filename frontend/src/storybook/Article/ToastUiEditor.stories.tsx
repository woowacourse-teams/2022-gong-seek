import ToastUiEditor from '@/components/@common/ToastUiEditor/ToastUiEditor';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Article/ToastUiEditor',
	component: ToastUiEditor,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<{ initContent: string }> = (args) => <ToastUiEditor {...args} />;

export const DefaultEditor = Template.bind({});
DefaultEditor.args = {
	initContent: '토스트 에디터입니다.',
};
