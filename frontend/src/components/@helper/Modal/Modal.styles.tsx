import styled from '@emotion/styled';

export const Dimmer = styled.div`
	position: fixed;

	height: 100vh;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;

	background-color: ${({ theme }) => theme.colors.GRAY_500};

	z-index: ${({ theme }) => theme.zIndex.MENU_SLIDER_BACKGROUND};
`;

export const Container = styled.div<{ isMobileOnly: boolean }>`
	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		display: ${({ isMobileOnly }) => (isMobileOnly ? 'none' : 'block')};
	}
`;
