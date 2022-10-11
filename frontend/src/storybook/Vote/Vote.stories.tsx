import { VoteHandler } from '@/mock/vote';
import Vote from '@/pages/Discussion/Vote/Vote';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/index',
	component: Vote,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<{ articleId: string }> = (args) => <Vote {...args} />;

export const DefaultVote = Template.bind({});
DefaultVote.args = {
	articleId: '1',
};
DefaultVote.parameters = {
	msw: {
		handlers: VoteHandler,
	},
};
