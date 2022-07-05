import ToastUiEditor from '@/pages/WritingArticles/ToastUiEditor/ToastUiEditor';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/ToastUiEditor',
	component: ToastUiEditor,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <ToastUiEditor {...args} />;

export const DefaultEditor = Template.bind({});
DefaultEditor.args = {};
