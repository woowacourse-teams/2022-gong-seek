import { VoteHandler } from '@/mock/vote';
import Vote from '@/pages/Discussion/Vote/Vote';
import { Meta, Story } from '@storybook/react';

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
