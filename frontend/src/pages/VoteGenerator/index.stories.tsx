import { Meta, Story } from '@storybook/react';
import VoteGenerator from '@/pages/VoteGenerator';

export default {
	title: 'pages/VoteGenerator',
	component: VoteGenerator,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <VoteGenerator {...args} />;

export const DefaultVoteGenerator = Template.bind({});
DefaultVoteGenerator.args = {};
