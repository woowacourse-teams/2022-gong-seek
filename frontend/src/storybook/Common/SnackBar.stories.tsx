import { useSetRecoilState } from 'recoil';

import SnackBar from '@/components/@common/SnackBar/SnackBar';
import { snackBarState } from '@/store/snackBarState';
import { Meta, Story } from '@storybook/react';

const modalRoot = document.createElement('div');
modalRoot.setAttribute('id', 'snack-bar');
document.body.append(modalRoot);

export default {
	title: 'Common/SnackBar',
	component: SnackBar,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
	recoilFlow: {
		filter: {
			keys: ['snackBarState'],
			isOpen: true,
		},
	},
} as Meta;

const MockSnackBar = () => {
	const setSnackBarState = useSetRecoilState(snackBarState);
	setSnackBarState({
		isOpen: true,
		message: '테스트입니다.',
	});

	return <SnackBar />;
};

const Template: Story = () => <MockSnackBar />;

export const DefaultSnackBar = Template.bind({});
