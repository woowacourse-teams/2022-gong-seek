import { Meta, Story } from '@storybook/react';
import ErrorDetail from '@/pages/ErrorDetail/index';

export default {
	title: 'pages/ErrorDetail',
	component: ErrorDetail,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <ErrorDetail {...args} />;

export const DefaultComment = Template.bind({});
