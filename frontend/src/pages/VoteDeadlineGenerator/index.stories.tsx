import VoteDeadlineGenerator from '@/pages/VoteDeadlineGenerator';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/VoteDeadlineGenerator',
	component: VoteDeadlineGenerator,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <VoteDeadlineGenerator {...args} />;

export const DefaultVoteDeadlineGenerator = Template.bind({});
DefaultVoteDeadlineGenerator.args = {};
