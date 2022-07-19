import { Meta, Story } from '@storybook/react';
import Detail from '@/pages/Detail/index';
import Vote from '../Discussion/Vote/Vote';

export default {
	title: 'pages/Detail',
	component: Detail,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <Detail {...args} />;

export const ErrorDetail = Template.bind({});

export const DiscussionDetail = Template.bind({});
DiscussionDetail.args = {
	children: <Vote articleId="4" />,
};
