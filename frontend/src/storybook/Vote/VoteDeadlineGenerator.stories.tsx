import { BrowserRouter } from 'react-router-dom';
import { withRouter } from 'storybook-addon-react-router-v6';

import { URL } from '@/constants/url';
import VoteDeadlineGenerator from '@/pages/VoteDeadlineGenerator';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/VoteDeadlineGenerator',
	component: VoteDeadlineGenerator,
	decorators: [
		withRouter,
		(Story) => (
			<div style={{ width: '320px' }} key="vote-deadline-generator">
				<Story />
			</div>
		),
	],
	parameters: {
		reactRouter: {
			routePath: URL.VOTE_DEADLINE_GENERATOR,
			routeState: { articleId: '0', items: ['투표1', '투표2', '투표3'] },
		},
	},
} as Meta;

const Template: Story = (args) => <VoteDeadlineGenerator {...args} />;

export const DefaultVoteDeadlineGenerator = Template.bind({});
DefaultVoteDeadlineGenerator.args = {};
