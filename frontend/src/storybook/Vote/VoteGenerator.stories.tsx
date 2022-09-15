import VoteGenerator from '@/pages/VoteGenerator';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/VoteGenerator',
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
