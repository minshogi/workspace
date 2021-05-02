import glfw
import sys
import pdb
import math
from OpenGL.GL import *
from OpenGL.GLU import *
from OpenGL.arrays import ArrayDatatype
import time
import numpy as np
import ctypes
from PIL.Image import open
import OBJ
from Ray import *


# global variables

### Additional variable ###
cowControlPoints = []

currentPoint = None
isInitial = True
isPickFinished = False

animStartTime = None
############################

wld2cam=[]
cam2wld=[]
cow2wld=None
cursorOnCowBoundingBox=False
pickInfo=None
floorTexID=0
cameras= [
	[28,18,28, 0,2,0, 0,1,0],   
	[28,18,-28, 0,2,0, 0,1,0], 
	[-28,18,28, 0,2,0, 0,1,0], 
	[-12,12,0, 0,2,0, 0,1,0],  
	[0,100,0,  0,0,0, 1,0,0]
]
camModel=None
cowModel=None
H_DRAG=1
V_DRAG=2
# dragging state
isDrag=0



class PickInfo:
    def __init__(self, cursorRayT, cowPickPosition, cowPickConfiguration, cowPickPositionLocal):
        self.cursorRayT=cursorRayT
        self.cowPickPosition=cowPickPosition.copy()
        self.cowPickConfiguration=cowPickConfiguration.copy()
        self.cowPickPositionLocal=cowPickPositionLocal.copy()

def vector3(x,y,z):
    return np.array((x,y,z))
def position3(v):
    # divide by w
    w=v[3]
    return vector3(v[0]/w, v[1]/w, v[2]/w)

def vector4(x,y,z):
    return np.array((x,y,z,1))

def rotate(m,v):
    return m[0:3, 0:3]@v
def transform(m, v):
    return position3(m@np.append(v,1))

def getTranslation(m):
    return m[0:3,3]
def setTranslation(m,v):
    m[0:3,3]=v

def makePlane( a,  b,  n):
    v=a.copy()
    for i in range(3):
        if n[i]==1.0:
            v[i]=b[i];
        elif n[i]==-1.0:
            v[i]=a[i];
        else:
            assert(n[i]==0.0);
            
    return Plane(rotate(cow2wld,n),transform(cow2wld,v));

def onKeyPress( window, key, scancode, action, mods):
    global cameraIndex
    if action==glfw.RELEASE:
        return ; # do nothing
    # If 'c' or space bar are pressed, alter the camera.
    # If a number is pressed, alter the camera corresponding the number.
    if key==glfw.KEY_C or key==glfw.KEY_SPACE:
        print( "Toggle camera %s\n"% cameraIndex );
        cameraIndex += 1;

    if cameraIndex >= len(wld2cam):
        cameraIndex = 0;



def drawOtherCamera():
    global cameraIndex,wld2cam, camModel
    for i in range(len(wld2cam)):
        if (i != cameraIndex):
            glPushMatrix();												# Push the current matrix on GL to stack. The matrix is wld2cam[cameraIndex].matrix().
            glMultMatrixd(cam2wld[i].T)
            drawFrame(5);											# Draw x, y, and z axis.
            frontColor = [0.2, 0.2, 0.2, 1.0];
            glEnable(GL_LIGHTING);									
            glMaterialfv(GL_FRONT, GL_AMBIENT, frontColor);			# Set ambient property frontColor.
            glMaterialfv(GL_FRONT, GL_DIFFUSE, frontColor);			# Set diffuse property frontColor.
            glScaled(0.5,0.5,0.5);										# Reduce camera size by 1/2.
            glTranslated(1.1,1.1,0.0);									# Translate it (1.1, 1.1, 0.0).
            camModel.render()
            glPopMatrix();												# Call the matrix on stack. wld2cam[cameraIndex].matrix() in here.

def drawFrame(leng):
    glDisable(GL_LIGHTING);	# Lighting is not needed for drawing axis.
    glBegin(GL_LINES);		# Start drawing lines.
    glColor3d(1,0,0);		# color of x-axis is red.
    glVertex3d(0,0,0);			
    glVertex3d(leng,0,0);	# Draw line(x-axis) from (0,0,0) to (len, 0, 0). 
    glColor3d(0,1,0);		# color of y-axis is green.
    glVertex3d(0,0,0);			
    glVertex3d(0,leng,0);	# Draw line(y-axis) from (0,0,0) to (0, len, 0).
    glColor3d(0,0,1);		# color of z-axis is  blue.
    glVertex3d(0,0,0);
    glVertex3d(0,0,leng);	# Draw line(z-axis) from (0,0,0) - (0, 0, len).
    glEnd();			# End drawing lines.

#*********************************************************************************
# Draw 'cow' object.
#*********************************************************************************/
def drawCow(_cow2wld, drawBB):

    glPushMatrix();		# Push the current matrix of GL into stack. This is because the matrix of GL will be change while drawing cow.

    # The information about location of cow to be drawn is stored in cow2wld matrix.
    # (Project2 hint) If you change the value of the cow2wld matrix or the current matrix, cow would rotate or move.
    glMultMatrixd(_cow2wld.T)

    drawFrame(5);										# Draw x, y, and z axis.
    frontColor = [0.8, 0.2, 0.9, 1.0];
    glEnable(GL_LIGHTING);
    glMaterialfv(GL_FRONT, GL_AMBIENT, frontColor);		# Set ambient property frontColor.
    glMaterialfv(GL_FRONT, GL_DIFFUSE, frontColor);		# Set diffuse property frontColor.
    cowModel.render()	# Draw cow. 
    glDisable(GL_LIGHTING);
    if drawBB:
        glBegin(GL_LINES);
        glColor3d(1,1,1);
        cow=cowModel
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmax[2]);

        glColor3d(1,1,1);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmax[2]);

        glColor3d(1,1,1);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmax[2]);


        glColor3d(1,1,1);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmax[2]);

        glColor3d(1,1,1);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmin[1], cow.bbmax[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmin[0], cow.bbmax[1], cow.bbmax[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmin[2]);
        glVertex3d( cow.bbmax[0], cow.bbmax[1], cow.bbmax[2]);
        glEnd();
    glPopMatrix();			# Pop the matrix in stack to GL. Change it the matrix before drawing cow.
def drawFloor():

    glDisable(GL_LIGHTING);

    # Set color of the floor.
    # Assign checker-patterned texture.
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, floorTexID );

    # Draw the floor. Match the texture's coordinates and the floor's coordinates resp. 
    nrep=4
    glBegin(GL_POLYGON);
    glTexCoord2d(0,0);
    glVertex3d(-12,-0.1,-12);		# Texture's (0,0) is bound to (-12,-0.1,-12).
    glTexCoord2d(nrep,0);
    glVertex3d( 12,-0.1,-12);		# Texture's (1,0) is bound to (12,-0.1,-12).
    glTexCoord2d(nrep,nrep);
    glVertex3d( 12,-0.1, 12);		# Texture's (1,1) is bound to (12,-0.1,12).
    glTexCoord2d(0,nrep);
    glVertex3d(-12,-0.1, 12);		# Texture's (0,1) is bound to (-12,-0.1,12).
    glEnd();

    glDisable(GL_TEXTURE_2D);	
    drawFrame(5);				# Draw x, y, and z axis.

def display():
    global cameraIndex, cow2wld, isDrag, isInitial, isPickFinished, cowControlPoints

    glClearColor(0.8, 0.9, 0.9, 1.0)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);				# Clear the screen
    # set viewing transformation.
    glLoadMatrixd(wld2cam[cameraIndex].T)

    drawOtherCamera();													# Locate the camera's position, and draw all of them.
    drawFloor();													# Draw floor.

    # TODO: 
    # update cow2wld here to animate the cow.
    # animTime=glfw.get_time()-animStartTime;
    # print(animTime)
    if isPickFinished :
        animTime = glfw.get_time() - animStartTime
        spline = getSplinePosition(animTime)
        # print(cow2wld)
        rotateCows(animTime)
        setTranslation(cow2wld, spline)

        drawCow(cow2wld, None)
        if animTime >= len(cowControlPoints) : # Finish
            isPickFinished = False
            isInitial = True
            isDrag = 0
            cow2wld = cowControlPoints[0]
            cowControlPoints = []
    else :
        drawCow(cow2wld, cursorOnCowBoundingBox)
        for i in range(len(cowControlPoints)):
            drawCow(cowControlPoints[i], None)												# Draw cow.

    glFlush();



def getSplinePosition(animTime):
    global cowControlPoints

    idx = int(animTime)
    t = animTime - idx

    catmullRomMatrix = np.array([[-.5, 1.5, -1.5, .5],
              [1., -2.5, 2., -.5],
              [-.5, 0., .5, 0.],
              [0., 1., 0., 0.]])
    timeToInt = int(animTime)
    t = animTime - timeToInt
    points = []

    # get next ControlPoint to translate
    def getControlPoint(t : int):
        global cowControlPoints
        i = 0 if t < 0 else t if t < len(cowControlPoints) else len(cowControlPoints) - 1
        return getTranslation(cowControlPoints[i])


    for i in range(4):
        points.append(getControlPoint(timeToInt + i - 1))

    P = np.array([pow(t, 3), pow(t,2), t, 1])
    Q = np.vstack((points[0], points[1], points[2], points[3]))

    return P @ catmullRomMatrix @ Q



def reshape(window, w, h):
    width = w;
    height = h;
    glViewport(0, 0, width, height);
    glMatrixMode(GL_PROJECTION);            # Select The Projection Matrix
    glLoadIdentity();                       # Reset The Projection Matrix
    # Define perspective projection frustum
    aspect = width/(float)(height);
    gluPerspective(45, aspect, 1, 1024);
    matProjection=glGetDoublev(GL_PROJECTION_MATRIX).T
    glMatrixMode(GL_MODELVIEW);             # Select The Modelview Matrix
    glLoadIdentity();                       # Reset The Projection Matrix

def initialize(window):
    global cursorOnCowBoundingBox, floorTexID, cameraIndex, camModel, cow2wld, cowModel
    cursorOnCowBoundingBox=False;
    # Set up OpenGL state
    glShadeModel(GL_SMOOTH);         # Set Smooth Shading
    glEnable(GL_DEPTH_TEST);         # Enables Depth Testing
    glDepthFunc(GL_LEQUAL);          # The Type Of Depth Test To Do
    # Use perspective correct interpolation if available
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    # Initialize the matrix stacks
    width, height = glfw.get_window_size(window)
    reshape(window, width, height);
    # Define lighting for the scene
    lightDirection   = [1.0, 1.0, 1.0, 0];
    ambientIntensity = [0.1, 0.1, 0.1, 1.0];
    lightIntensity   = [0.9, 0.9, 0.9, 1.0];
    glLightfv(GL_LIGHT0, GL_AMBIENT, ambientIntensity);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, lightIntensity);
    glLightfv(GL_LIGHT0, GL_POSITION, lightDirection);
    glEnable(GL_LIGHT0);

    # initialize floor
    im = open('bricks.bmp')
    try:
        ix, iy, image = im.size[0], im.size[1], im.tobytes("raw", "RGB", 0, -1)
    except SystemError:
        ix, iy, image = im.size[0], im.size[1], im.tobytes("raw", "RGBX", 0, -1)

    # Make texture which is accessible through floorTexID. 
    floorTexID=glGenTextures( 1)
    glBindTexture(GL_TEXTURE_2D, floorTexID);		
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
    glTexImage2D(GL_TEXTURE_2D, 0, 3, ix, ix, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
    # initialize cow
    cowModel=OBJ.OBJrenderer("cow.obj")

    # initialize cow2wld matrix
    glPushMatrix();		        # Push the current matrix of GL into stack.
    glLoadIdentity();		        # Set the GL matrix Identity matrix.
    glTranslated(0,-cowModel.bbmin[1],-8);	# Set the location of cow.
    glRotated(-90, 0, 1, 0);		# Set the direction of cow. These information are stored in the matrix of GL.
    cow2wld=glGetDoublev(GL_MODELVIEW_MATRIX).T # convert column-major to row-major 
    glPopMatrix();			# Pop the matrix on stack to GL.


    # intialize camera model.
    camModel=OBJ.OBJrenderer("camera.obj")


    # initialize camera frame transforms.

    cameraCount=len(cameras)
    for i in range(cameraCount):
        # 'c' points the coordinate of i-th camera.
        c = cameras[i];										
        glPushMatrix();													# Push the current matrix of GL into stack.
        glLoadIdentity();												# Set the GL matrix Identity matrix.
        gluLookAt(c[0],c[1],c[2], c[3],c[4],c[5], c[6],c[7],c[8]);		# Setting the coordinate of camera.
        wld2cam.append(glGetDoublev(GL_MODELVIEW_MATRIX).T)
        glPopMatrix();													# Transfer the matrix that was pushed the stack to GL.
        cam2wld.append(np.linalg.inv(wld2cam[i]))
    cameraIndex = 0;

def onMouseButton(window,button, state, mods):
    global isDrag, V_DRAG, H_DRAG, cow2wld, isInitial, isPickFinished
    GLFW_DOWN=1;
    GLFW_UP=0;
    x, y=glfw.get_cursor_pos(window)

    if button == glfw.MOUSE_BUTTON_LEFT:
        if state == GLFW_DOWN:
            isDrag = V_DRAG;
            # print("Left mouse down-click at %d %d\n" % (x, y))
            # start vertical dragging
        elif state == GLFW_UP and isDrag != 0:
            isInitial = False
            if not isPickFinished and not isInitial:
                cowControlPoints.append(cow2wld.copy())
                # print('cowControlPoints' + str(len(cowControlPoints)))
                if len(cowControlPoints) >= 6:
                    isPickFinished = True
                    setInitialState()

            isDrag = H_DRAG;

            # print("Left mouse up\n");
            # start horizontal dragging using mouse-move events.
    elif button == glfw.MOUSE_BUTTON_RIGHT:
        if state == GLFW_DOWN:
            pass
            # print("Right mouse click at (%d, %d)\n" % (x, y));

def onMouseDrag(window, x, y):
    global isDrag,cursorOnCowBoundingBox, pickInfo, cow2wld
    if isDrag: 
        print( "in drag mode %d\n"% isDrag);
        if  isDrag==V_DRAG:
            # vertical dragging
            # TODO:
            # create a dragging plane perpendicular to the ray direction, 
            # and test intersection with the screen ray.
            if cursorOnCowBoundingBox:
                ray = screenCoordToRay(window, x, y)
                pp = pickInfo
                p = Plane(np.array((1, 0, 0)), getTranslation(cow2wld))
                c = ray.intersectsPlane(p)

                T = np.eye(4)
                currentPos = np.array([0, ray.getPoint(c[1])[1] - getTranslation(cow2wld)[1], 0])
                setTranslation(T, currentPos)
                cow2wld = T @ cow2wld
                # print('vdrag')

        else:
            # horizontal dragging
            # Hint: read carefully the following block to implement vertical dragging.
            if cursorOnCowBoundingBox:
                ray=screenCoordToRay(window, x, y);
                pp=pickInfo;
                p=Plane(np.array((0,1,0)), pp.cowPickPosition);
                c=ray.intersectsPlane(p);

                currentPos=ray.getPoint(c[1])
                print(pp.cowPickPosition, currentPos)
                print(pp.cowPickConfiguration, cow2wld)
                
                T=np.eye(4)
                setTranslation(T, currentPos-pp.cowPickPosition)
                cow2wld=T@pp.cowPickConfiguration;


    else:
        ray=screenCoordToRay(window, x, y)

        planes=[];
        cow=cowModel
        bbmin=cow.bbmin
        bbmax=cow.bbmax

        planes.append(makePlane(bbmin, bbmax, vector3(0,1,0)));
        planes.append(makePlane(bbmin, bbmax, vector3(0,-1,0)));
        planes.append(makePlane(bbmin, bbmax, vector3(1,0,0)));
        planes.append(makePlane(bbmin, bbmax, vector3(-1,0,0)));
        planes.append(makePlane(bbmin, bbmax, vector3(0,0,1)));
        planes.append(makePlane(bbmin, bbmax, vector3(0,0,-1)));


        o=ray.intersectsPlanes(planes);
        cursorOnCowBoundingBox=o[0]
        cowPickPosition=ray.getPoint(o[1])
        cowPickLocalPos=transform(np.linalg.inv(cow2wld),cowPickPosition)
        pickInfo=PickInfo( o[1], cowPickPosition, cow2wld, cowPickLocalPos)


# Setting Initial State before Moving
def setInitialState():
    global currentPoint, cowControlPoints, animStartTime
    animStartTime = glfw.get_time()
    currentPoint = cow2wld
    # print(cow2wld)
    tmp = []
    for i in range(3):
        for ccp in cowControlPoints:
            tmp.append(ccp)
            # print(tmp)

    tmp.append(cowControlPoints[0])
    cowControlPoints = tmp

# rotate cow when turn direction
def rotateCows(animTime):
    global cowControlPoints, currentPoint, cow2wld
    origin = getSplinePosition(animTime - .2)
    dest = getSplinePosition(animTime + .2)
    direction = dest - origin

    a = np.array([.0, .1, .0])
    u = normalize(np.cross(direction, a))
    v = normalize(np.cross(u, direction))
    w = np.cross(v, u)
    Matrix = np.eye(4)

    for i in range(3):
        Matrix[i][0] = w[i]
        Matrix[i][1] = v[i]
        Matrix[i][2] = u[i]
    #print('w', w)
    #print('v', v)
    #print('u', u)
    #print('matrix = ',Matrix)


    setTranslation(Matrix, getTranslation(cow2wld))
    cow2wld = Matrix


def screenCoordToRay(window, x, y):
    width, height = glfw.get_window_size(window)

    matProjection=glGetDoublev(GL_PROJECTION_MATRIX).T
    matProjection=matProjection@wld2cam[cameraIndex]; # use @ for matrix mult.
    invMatProjection=np.linalg.inv(matProjection);
    # -1<=v.x<1 when 0<=x<width
    # -1<=v.y<1 when 0<=y<height
    vecAfterProjection =vector4(
            (float(x - 0))/(float(width))*2.0-1.0,
            -1*(((float(y - 0))/float(height))*2.0-1.0),
            -10)

    #std::cout<<"cowPosition in clip coordinate (NDC)"<<matProjection*cow2wld.getTranslation()<<std::endl;
	
    vecBeforeProjection=position3(invMatProjection@vecAfterProjection);

    rayOrigin=getTranslation(cam2wld[cameraIndex])
    return Ray(rayOrigin, normalize(vecBeforeProjection-rayOrigin))

def main():
    if not glfw.init():
        print ('GLFW initialization failed')
        sys.exit(-1)
    width = 800;
    height = 600;
    window = glfw.create_window(width, height, 'modern opengl example', None, None)
    if not window:
        glfw.terminate()
        sys.exit(-1)

    glfw.make_context_current(window)
    glfw.set_key_callback(window, onKeyPress)
    glfw.set_mouse_button_callback(window, onMouseButton)
    glfw.set_cursor_pos_callback(window, onMouseDrag)
    glfw.swap_interval(1)

    initialize(window);						
    while not glfw.window_should_close(window):
        glfw.poll_events()
        display()

        glfw.swap_buffers(window)

    glfw.terminate()
if __name__ == "__main__":
    main()
