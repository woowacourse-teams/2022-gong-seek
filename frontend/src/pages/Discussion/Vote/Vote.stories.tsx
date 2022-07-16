import { Meta, Story } from '@storybook/react';
import Vote from '@/pages/Discussion/Vote/Vote';
import { VoteHandler } from '@/mock/vote';

export default {
	title: 'components/Vote',
	component: Vote,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <Vote {...args} />;

export const DefaultVote = Template.bind({});
DefaultVote.args = {};
DefaultVote.parameters = {
	msw: {
		handlers: VoteHandler,
	},
};
z;
